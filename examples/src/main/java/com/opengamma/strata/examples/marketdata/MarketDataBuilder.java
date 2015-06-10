/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.strata.examples.marketdata;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.FxRate;
import com.opengamma.strata.basics.date.DayCount;
import com.opengamma.strata.basics.date.DayCounts;
import com.opengamma.strata.basics.market.FxRateId;
import com.opengamma.strata.basics.market.ObservableId;
import com.opengamma.strata.collect.Messages;
import com.opengamma.strata.collect.io.ResourceLocator;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeries;
import com.opengamma.strata.collect.tuple.Pair;
import com.opengamma.strata.engine.config.MarketDataRule;
import com.opengamma.strata.engine.config.MarketDataRules;
import com.opengamma.strata.engine.marketdata.BaseMarketData;
import com.opengamma.strata.engine.marketdata.BaseMarketDataBuilder;
import com.opengamma.strata.examples.marketdata.curve.RatesCurvesCsvLoader;
import com.opengamma.strata.examples.marketdata.timeseries.FixingSeriesCsvLoader;
import com.opengamma.strata.function.marketdata.mapping.MarketDataMappingsBuilder;
import com.opengamma.strata.market.curve.Curve;
import com.opengamma.strata.market.curve.CurveGroupName;
import com.opengamma.strata.market.id.DiscountCurveId;
import com.opengamma.strata.market.id.RateCurveId;
import com.opengamma.strata.market.id.ZeroRateDiscountFactorsId;
import com.opengamma.strata.market.value.ZeroRateDiscountFactors;

/**
 * Builds a market data snapshot from user-editable files in a prescribed directory structure.
 * <p>
 * Descendants of this class provide the ability to source this directory structure from any
 * location.
 * <p>
 * The directory structure must look like:
 * <ul>
 *   <li>root
 *   <ul>
 *     <li>curves
 *     <ul>
 *       <li>groups.csv
 *       <li>settings.csv
 *       <li>one or more curve CSV files
 *     </ul>
 *     <li>historical-fixings
 *     <ul>
 *       <li>one or more time-series CSV files
 *     </ul>
 *   </ul>
 * </ul>
 */
public abstract class MarketDataBuilder {

  /** The name of the subdirectory containing historical fixings. */
  private static final String HISTORICAL_FIXINGS_DIR = "historical-fixings";

  /** The name of the subdirectory containing calibrated rates curves. */
  private static final String CURVES_DIR = "curves";
  /** The name of the curve groups file. */
  private static final String CURVES_GROUPS_FILE = "groups.csv";
  /** The name of the curve settings file. */
  private static final String CURVES_SETTINGS_FILE = "settings.csv";

  /**
   * Creates an instance from a given classpath resource root location.
   * <p>
   * This is designed to handle resource roots which may physically correspond to a directory on
   * disk, or be located within a jar file.
   * 
   * @param resourceRoot  the resource root path
   * @return the market data builder
   */
  public static MarketDataBuilder ofResource(String resourceRoot) {
    String qualifiedResourceRoot = resourceRoot.startsWith("/") ? resourceRoot : "/" + resourceRoot;

    URL url = MarketDataBuilder.class.getResource(qualifiedResourceRoot);
    if (url.getProtocol() != null && "jar".equals(url.getProtocol().toLowerCase())) {
      // Inside a JAR
      int classSeparatorIdx = url.getFile().indexOf("!");
      if (classSeparatorIdx == -1) {
        throw new IllegalArgumentException(
            Messages.format("Unexpected JAR file URL: {}", url));
      }
      String jarPath = url.getFile().substring("file:".length(), classSeparatorIdx);
      File jarFile;
      try {
        jarFile = new File(jarPath);
      } catch (Exception e) {
        throw new IllegalArgumentException(
            Messages.format("Unable to create file for JAR: {}", jarPath), e);
      }
      return new JarMarketDataBuilder(jarFile, resourceRoot);
    } else {
      // Resource is on disk
      File file = new File(url.getPath());
      return new DirectoryMarketDataBuilder(file.toPath());
    }
  }

  /**
   * Creates an instance from a given directory root.
   * 
   * @param rootPath  the root directory
   * @return the market data builder
   */
  public static MarketDataBuilder ofPath(Path rootPath) {
    return new DirectoryMarketDataBuilder(rootPath);
  }

  //-------------------------------------------------------------------------
  /**
   * Builds a market data snapshot from this environment.
   * 
   * @return the snapshot
   */
  public BaseMarketData buildSnapshot(LocalDate marketDataDate) {
    BaseMarketDataBuilder builder = BaseMarketData.builder(marketDataDate);
    loadFixingSeries(builder);
    loadRatesCurves(builder, marketDataDate);
    loadFxRates(builder);
    return builder.build();
  }

  /**
   * Gets the market data rules to use with this environment.
   * 
   * @return the market data rules
   */
  public MarketDataRules rules() {
    // TODO - should be loaded from a CSV file - format to be defined
    return MarketDataRules.of(
        MarketDataRule.anyTarget(
            MarketDataMappingsBuilder.create()
                .curveGroup(CurveGroupName.of("Default"))
                .build()));
  }

  //-------------------------------------------------------------------------
  private void loadFixingSeries(BaseMarketDataBuilder builder) {
    Collection<ResourceLocator> fixingSeriesResources = getAllResources(HISTORICAL_FIXINGS_DIR);
    Map<ObservableId, LocalDateDoubleTimeSeries> fixingSeries = FixingSeriesCsvLoader.loadFixingSeries(fixingSeriesResources);
    builder.addAllTimeSeries(fixingSeries);
  }

  private void loadRatesCurves(BaseMarketDataBuilder builder, LocalDate marketDataDate) {
    ResourceLocator curveGroupsResource = getResource(CURVES_DIR, CURVES_GROUPS_FILE);
    ResourceLocator curveSettingsResource = getResource(CURVES_DIR, CURVES_SETTINGS_FILE);

    Collection<ResourceLocator> curvesResources = getAllResources(CURVES_DIR)
        .stream()
        .filter(
            res -> !res.getLocator().endsWith(CURVES_GROUPS_FILE) && !res.getLocator().endsWith(CURVES_SETTINGS_FILE))
        .collect(Collectors.toList());

    Map<RateCurveId, Curve> ratesCurves = RatesCurvesCsvLoader
        .loadCurves(curveGroupsResource, curveSettingsResource, curvesResources, marketDataDate);

    Map<ZeroRateDiscountFactorsId, ZeroRateDiscountFactors> zeroRateDiscountFactors =
        ratesCurves.entrySet().stream()
            .filter(e -> e.getKey() instanceof DiscountCurveId)
            .map(e -> Pair.of((DiscountCurveId) e.getKey(), e.getValue()))
            .collect(Collectors.toMap(
                e -> toZeroRateDiscountFactorsId(e.getFirst()),
                e -> toZeroRateDiscountFactors(e.getFirst(), e.getSecond(), marketDataDate)));

    builder.addAllValues(ratesCurves);
    builder.addAllValues(zeroRateDiscountFactors);
  }

  private ZeroRateDiscountFactorsId toZeroRateDiscountFactorsId(DiscountCurveId curveId) {
    return ZeroRateDiscountFactorsId.of(
        curveId.getCurrency(), curveId.getCurveGroupName(), curveId.getMarketDataFeed());
  }

  private ZeroRateDiscountFactors toZeroRateDiscountFactors(DiscountCurveId curveId, Curve curve, LocalDate valuationDate) {
    // TODO - why is DayCount needed?
    // It's already encoded in the year fractions in the curve.
    // Should be exposed via the Curve interface if required.
    // Hard-coding here for now.
    DayCount dayCount = DayCounts.ACT_ACT_ISDA;

    return ZeroRateDiscountFactors.of(curveId.getCurrency(), valuationDate, dayCount, curve);
  }

  private void loadFxRates(BaseMarketDataBuilder builder) {
    // TODO - load from CSV file - format to be defined
    builder.addValue(FxRateId.of(Currency.GBP, Currency.USD), FxRate.of(Currency.GBP, Currency.USD, 1.61));
  }

  //-------------------------------------------------------------------------
  /**
   * Gets all available resources from a given subdirectory.
   * 
   * @param subdirectoryName  the name of the subdirectory
   * @return a collection of locators for the resources in the subdirectory
   */
  protected abstract Collection<ResourceLocator> getAllResources(String subdirectoryName);

  /**
   * Gets a specific resource from a given subdirectory.
   * 
   * @param subdirectoryName  the name of the subdirectory
   * @param resourceName  the name of the resource
   * @return a locator for the requested resource
   */
  protected abstract ResourceLocator getResource(String subdirectoryName, String resourceName);

}
