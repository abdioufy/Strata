/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.platform.finance.trade.swap;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.DerivedProperty;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.platform.finance.trade.AssetClass;
import com.opengamma.platform.finance.trade.Trade;
import com.opengamma.platform.finance.trade.TradeType;

/**
 * A trade representing an interest rate swap.
 * <p>
 * An interest rate swap takes place between two counterparties who agree to exchange
 * two streams of future interest payments. Typically, one leg has a fixed rate
 * and the other a floating rate, however other combinations are possible.
 */
@BeanDefinition
public final class SwapTrade
    implements Trade, ImmutableBean, Serializable {

  /**
   * The trade type constant for this class.
   */
  public static final TradeType TYPE = TradeType.of("Swap");

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The trade date.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final LocalDate tradeDate;
  /**
   * The trade time with offset, null if not known.
   */
  @PropertyDefinition
  private final OffsetTime tradeTime;
  /**
   * The general purpose trade attributes.
   * Most data in the trade is available as bean properties.
   * Attributes are used to tag the object with additional information.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final ImmutableMap<String, String> attributes;

  /**
   * The first leg of the trade.
   */
  @PropertyDefinition(validate = "notNull")
  private final SwapLeg leg1;
  /**
   * The second leg of the trade.
   */
  @PropertyDefinition(validate = "notNull")
  private final SwapLeg leg2;

  /**
   * Creates a swap trade.
   * 
   * @param tradeDate  the date of the trade
   * @param leg1  the first leg
   * @param leg2  the second leg
   * @return the trade
   */
  public static SwapTrade of(LocalDate tradeDate, SwapLeg leg1, SwapLeg leg2) {
    return builder().leg1(leg1).leg2(leg2).build();
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the trade type.
   * 
   * @return return 'Swap'
   */
  @Override
  @DerivedProperty
  public TradeType getTradeType() {
    return TYPE;
  }

  /**
   * Gets the asset class.
   * 
   * @return {@link AssetClass#SWAP}
   */
  @Override
  @DerivedProperty
  public AssetClass getAssetClass() {
    return AssetClass.SWAP;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SwapTrade}.
   * @return the meta-bean, not null
   */
  public static SwapTrade.Meta meta() {
    return SwapTrade.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SwapTrade.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static SwapTrade.Builder builder() {
    return new SwapTrade.Builder();
  }

  private SwapTrade(
      LocalDate tradeDate,
      OffsetTime tradeTime,
      Map<String, String> attributes,
      SwapLeg leg1,
      SwapLeg leg2) {
    JodaBeanUtils.notNull(tradeDate, "tradeDate");
    JodaBeanUtils.notNull(attributes, "attributes");
    JodaBeanUtils.notNull(leg1, "leg1");
    JodaBeanUtils.notNull(leg2, "leg2");
    this.tradeDate = tradeDate;
    this.tradeTime = tradeTime;
    this.attributes = ImmutableMap.copyOf(attributes);
    this.leg1 = leg1;
    this.leg2 = leg2;
  }

  @Override
  public SwapTrade.Meta metaBean() {
    return SwapTrade.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the trade date.
   * @return the value of the property, not null
   */
  @Override
  public LocalDate getTradeDate() {
    return tradeDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the trade time with offset, null if not known.
   * @return the value of the property
   */
  public OffsetTime getTradeTime() {
    return tradeTime;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the general purpose trade attributes.
   * Most data in the trade is available as bean properties.
   * Attributes are used to tag the object with additional information.
   * @return the value of the property, not null
   */
  @Override
  public ImmutableMap<String, String> getAttributes() {
    return attributes;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the first leg of the trade.
   * @return the value of the property, not null
   */
  public SwapLeg getLeg1() {
    return leg1;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the second leg of the trade.
   * @return the value of the property, not null
   */
  public SwapLeg getLeg2() {
    return leg2;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SwapTrade other = (SwapTrade) obj;
      return JodaBeanUtils.equal(getTradeDate(), other.getTradeDate()) &&
          JodaBeanUtils.equal(getTradeTime(), other.getTradeTime()) &&
          JodaBeanUtils.equal(getAttributes(), other.getAttributes()) &&
          JodaBeanUtils.equal(getLeg1(), other.getLeg1()) &&
          JodaBeanUtils.equal(getLeg2(), other.getLeg2()) &&
          JodaBeanUtils.equal(getTradeType(), other.getTradeType()) &&
          JodaBeanUtils.equal(getAssetClass(), other.getAssetClass());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getTradeDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getTradeTime());
    hash += hash * 31 + JodaBeanUtils.hashCode(getAttributes());
    hash += hash * 31 + JodaBeanUtils.hashCode(getLeg1());
    hash += hash * 31 + JodaBeanUtils.hashCode(getLeg2());
    hash += hash * 31 + JodaBeanUtils.hashCode(getTradeType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getAssetClass());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(256);
    buf.append("SwapTrade{");
    buf.append("tradeDate").append('=').append(getTradeDate()).append(',').append(' ');
    buf.append("tradeTime").append('=').append(getTradeTime()).append(',').append(' ');
    buf.append("attributes").append('=').append(getAttributes()).append(',').append(' ');
    buf.append("leg1").append('=').append(getLeg1()).append(',').append(' ');
    buf.append("leg2").append('=').append(getLeg2()).append(',').append(' ');
    buf.append("tradeType").append('=').append(getTradeType()).append(',').append(' ');
    buf.append("assetClass").append('=').append(JodaBeanUtils.toString(getAssetClass()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SwapTrade}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code tradeDate} property.
     */
    private final MetaProperty<LocalDate> tradeDate = DirectMetaProperty.ofImmutable(
        this, "tradeDate", SwapTrade.class, LocalDate.class);
    /**
     * The meta-property for the {@code tradeTime} property.
     */
    private final MetaProperty<OffsetTime> tradeTime = DirectMetaProperty.ofImmutable(
        this, "tradeTime", SwapTrade.class, OffsetTime.class);
    /**
     * The meta-property for the {@code attributes} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<String, String>> attributes = DirectMetaProperty.ofImmutable(
        this, "attributes", SwapTrade.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code leg1} property.
     */
    private final MetaProperty<SwapLeg> leg1 = DirectMetaProperty.ofImmutable(
        this, "leg1", SwapTrade.class, SwapLeg.class);
    /**
     * The meta-property for the {@code leg2} property.
     */
    private final MetaProperty<SwapLeg> leg2 = DirectMetaProperty.ofImmutable(
        this, "leg2", SwapTrade.class, SwapLeg.class);
    /**
     * The meta-property for the {@code tradeType} property.
     */
    private final MetaProperty<TradeType> tradeType = DirectMetaProperty.ofDerived(
        this, "tradeType", SwapTrade.class, TradeType.class);
    /**
     * The meta-property for the {@code assetClass} property.
     */
    private final MetaProperty<AssetClass> assetClass = DirectMetaProperty.ofDerived(
        this, "assetClass", SwapTrade.class, AssetClass.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "tradeDate",
        "tradeTime",
        "attributes",
        "leg1",
        "leg2",
        "tradeType",
        "assetClass");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 752419634:  // tradeDate
          return tradeDate;
        case 752903761:  // tradeTime
          return tradeTime;
        case 405645655:  // attributes
          return attributes;
        case 3317731:  // leg1
          return leg1;
        case 3317732:  // leg2
          return leg2;
        case 752919230:  // tradeType
          return tradeType;
        case 2103649384:  // assetClass
          return assetClass;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public SwapTrade.Builder builder() {
      return new SwapTrade.Builder();
    }

    @Override
    public Class<? extends SwapTrade> beanType() {
      return SwapTrade.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code tradeDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> tradeDate() {
      return tradeDate;
    }

    /**
     * The meta-property for the {@code tradeTime} property.
     * @return the meta-property, not null
     */
    public MetaProperty<OffsetTime> tradeTime() {
      return tradeTime;
    }

    /**
     * The meta-property for the {@code attributes} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<String, String>> attributes() {
      return attributes;
    }

    /**
     * The meta-property for the {@code leg1} property.
     * @return the meta-property, not null
     */
    public MetaProperty<SwapLeg> leg1() {
      return leg1;
    }

    /**
     * The meta-property for the {@code leg2} property.
     * @return the meta-property, not null
     */
    public MetaProperty<SwapLeg> leg2() {
      return leg2;
    }

    /**
     * The meta-property for the {@code tradeType} property.
     * @return the meta-property, not null
     */
    public MetaProperty<TradeType> tradeType() {
      return tradeType;
    }

    /**
     * The meta-property for the {@code assetClass} property.
     * @return the meta-property, not null
     */
    public MetaProperty<AssetClass> assetClass() {
      return assetClass;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 752419634:  // tradeDate
          return ((SwapTrade) bean).getTradeDate();
        case 752903761:  // tradeTime
          return ((SwapTrade) bean).getTradeTime();
        case 405645655:  // attributes
          return ((SwapTrade) bean).getAttributes();
        case 3317731:  // leg1
          return ((SwapTrade) bean).getLeg1();
        case 3317732:  // leg2
          return ((SwapTrade) bean).getLeg2();
        case 752919230:  // tradeType
          return ((SwapTrade) bean).getTradeType();
        case 2103649384:  // assetClass
          return ((SwapTrade) bean).getAssetClass();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code SwapTrade}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<SwapTrade> {

    private LocalDate tradeDate;
    private OffsetTime tradeTime;
    private Map<String, String> attributes = new HashMap<String, String>();
    private SwapLeg leg1;
    private SwapLeg leg2;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(SwapTrade beanToCopy) {
      this.tradeDate = beanToCopy.getTradeDate();
      this.tradeTime = beanToCopy.getTradeTime();
      this.attributes = new HashMap<String, String>(beanToCopy.getAttributes());
      this.leg1 = beanToCopy.getLeg1();
      this.leg2 = beanToCopy.getLeg2();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 752419634:  // tradeDate
          return tradeDate;
        case 752903761:  // tradeTime
          return tradeTime;
        case 405645655:  // attributes
          return attributes;
        case 3317731:  // leg1
          return leg1;
        case 3317732:  // leg2
          return leg2;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 752419634:  // tradeDate
          this.tradeDate = (LocalDate) newValue;
          break;
        case 752903761:  // tradeTime
          this.tradeTime = (OffsetTime) newValue;
          break;
        case 405645655:  // attributes
          this.attributes = (Map<String, String>) newValue;
          break;
        case 3317731:  // leg1
          this.leg1 = (SwapLeg) newValue;
          break;
        case 3317732:  // leg2
          this.leg2 = (SwapLeg) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public SwapTrade build() {
      return new SwapTrade(
          tradeDate,
          tradeTime,
          attributes,
          leg1,
          leg2);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code tradeDate} property in the builder.
     * @param tradeDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder tradeDate(LocalDate tradeDate) {
      JodaBeanUtils.notNull(tradeDate, "tradeDate");
      this.tradeDate = tradeDate;
      return this;
    }

    /**
     * Sets the {@code tradeTime} property in the builder.
     * @param tradeTime  the new value
     * @return this, for chaining, not null
     */
    public Builder tradeTime(OffsetTime tradeTime) {
      this.tradeTime = tradeTime;
      return this;
    }

    /**
     * Sets the {@code attributes} property in the builder.
     * @param attributes  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder attributes(Map<String, String> attributes) {
      JodaBeanUtils.notNull(attributes, "attributes");
      this.attributes = attributes;
      return this;
    }

    /**
     * Sets the {@code leg1} property in the builder.
     * @param leg1  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder leg1(SwapLeg leg1) {
      JodaBeanUtils.notNull(leg1, "leg1");
      this.leg1 = leg1;
      return this;
    }

    /**
     * Sets the {@code leg2} property in the builder.
     * @param leg2  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder leg2(SwapLeg leg2) {
      JodaBeanUtils.notNull(leg2, "leg2");
      this.leg2 = leg2;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("SwapTrade.Builder{");
      buf.append("tradeDate").append('=').append(JodaBeanUtils.toString(tradeDate)).append(',').append(' ');
      buf.append("tradeTime").append('=').append(JodaBeanUtils.toString(tradeTime)).append(',').append(' ');
      buf.append("attributes").append('=').append(JodaBeanUtils.toString(attributes)).append(',').append(' ');
      buf.append("leg1").append('=').append(JodaBeanUtils.toString(leg1)).append(',').append(' ');
      buf.append("leg2").append('=').append(JodaBeanUtils.toString(leg2));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}