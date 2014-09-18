/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.basics.date;

import java.time.LocalDate;

import com.opengamma.collect.ArgChecker;
import com.opengamma.collect.named.ExtendedEnum;

/**
 * Constants and implementations for standard day count conventions.
 * <p>
 * The purpose of each convention is to define how to convert dates into numeric year fractions.
 * The is of use when calculating accrued interest over time.
 */
public final class DayCounts {

  /**
   * The '1/1' day count, which always returns a day count of 1.
   * <p>
   * The result is always one.
   * <p>
   * Also known as 'One/One'.
   * Defined by the 2006 ISDA definitions 4.16a.
   */
  public static final DayCount ONE_ONE = Standard.ONE_ONE;
  /**
   * The 'Act/Act ISDA' day count, which divides the actual number of days in a
   * leap year by 366 and the actual number of days in a standard year by 365.
   * <p>
   * The result is calculated in two parts.
   * The actual number of days in the requested period that fall in a leap year is divided by 366.
   * The actual number of days in the requested period that fall in a standard year is divided by 365.
   * The result is the sum of the two.
   * The first day in the period is included, the last day is excluded.
   * <p>
   * Also known as 'Actual/Actual'.
   * Defined by the 2006 ISDA definitions 4.16b.
   */
  public static final DayCount ACT_ACT_ISDA = Standard.ACT_ACT_ISDA;
  /**
   * The 'Act/365 Actual' day count, which divides the actual number of days by 366
   * if a leap day is contained, or by 365 if not.
   * <p>
   * The result is a simple division.
   * The numerator is the actual number of days in the requested period.
   * The denominator is 366 if the period contains February 29th, if not it is 365.
   * The first day in the period is excluded, the last day is included.
   * <p>
   * Also known as 'Act/365A'.
   */
  public static final DayCount ACT_365_ACTUAL = Standard.ACT_365_ACTUAL;
  /**
   * The 'Act/360' day count, which divides the actual number of days by 360.
   * <p>
   * The result is a simple division.
   * The numerator is the actual number of days in the requested period.
   * The denominator is always 360.
   * <p>
   * Also known as 'Actual/360' or 'French'.
   * Defined by the 2006 ISDA definitions 4.16e and ICMA rule 251.1(i) part 1.
   */
  public static final DayCount ACT_360 = Standard.ACT_360;
  /**
   * The 'Act/364' day count, which divides the actual number of days by 364.
   * <p>
   * The result is a simple division.
   * The numerator is the actual number of days in the requested period.
   * The denominator is always 364.
   * <p>
   * Also known as 'Actual/364'.
   */
  public static final DayCount ACT_364 = Standard.ACT_364;
  /**
   * The 'Act/365' day count, which divides the actual number of days by 365 (fixed).
   * <p>
   * The result is a simple division.
   * The numerator is the actual number of days in the requested period.
   * The denominator is always 365.
   * <p>
   * Also known as 'Act/365F', 'Actual/365 Fixed' or 'English'.
   * Defined by the 2006 ISDA definitions 4.16d.
   */
  public static final DayCount ACT_365 = Standard.ACT_365;
  /**
   * The 'Act/365.25' day count, which divides the actual number of days by 365.25.
   * <p>
   * The result is a simple division.
   * The numerator is the actual number of days in the requested period.
   * The denominator is always 365.25.
   */
  public static final DayCount ACT_365_25 = Standard.ACT_365_25;
  /**
   * The 'NL/365' day count, which divides the actual number of days omitting leap days by 365.
   * <p>
   * The result is a simple division.
   * The numerator is the actual number of days in the requested period minus the number of occurrences of February 29.
   * The denominator is always 365.
   * The first day in the period is excluded, the last day is included.
   * <p>
   * Also known as 'Actual/365 No Leap'.
   */
  public static final DayCount NL_365 = Standard.NL_365;
  /**
   * The '30/360 ISDA' day count, which treats input day-of-month 31 specially.
   * <p>
   * The result is calculated as {@code (360 * deltaYear + 30 * deltaMonth + deltaDay) / 360}.
   * The deltaDay is then calculated once day-of-month adjustments have occurred.
   * If the second day-of-month is 31 and the first day-of-month is 30 or 31, change the second day-of-month to 30.
   * If the first day-of-month is 31, change the first day-of-month to 30.
   * <p>
   * Also known as '30/360 U.S. Municipal' or '30/360 Bond Basis'.
   * Defined by the 2006 ISDA definitions 4.16f.
   */
  public static final DayCount THIRTY_360_ISDA = Standard.THIRTY_360_ISDA;
  /**
   * The '30U/360' day count, which treats input day-of-month 31 and end of February specially.
   * <p>
   * The result is calculated as {@code (360 * deltaYear + 30 * deltaMonth + deltaDay) / 360}.
   * The deltaDay is then calculated once day-of-month adjustments have occurred.
   * If the schedule uses EOM convention and both dates are the last day of February,
   * change the second day-of-month to 30.
   * If the schedule uses EOM convention and the first date is the last day of February,
   * change the first day-of-month to 30.
   * If the second day-of-month is 31 and the first day-of-month is 30 or 31, change the second day-of-month to 30.
   * If the first day-of-month is 31, change the first day-of-month to 30.
   * <p>
   * This is the same as '30/360 ISDA' is the EOM convention does not apply
   * but with two additional end of February rules if the EOM does apply.
   * <p>
   * The method {@link DayCount#getDayCountFraction(LocalDate, LocalDate)} will assume
   * that the end-of-month rule applies.
   * <p>
   * Also known as '30/360 US', '30US/360' or '30/360 SIA'.
   */
  public static final DayCount THIRTY_U_360 = Standard.THIRTY_U_360;
  /**
   * The '30E/360 ISDA' day count, which treats input day-of-month 31 and end of February specially.
   * <p>
   * The result is calculated as {@code (360 * deltaYear + 30 * deltaMonth + deltaDay) / 360}.
   * The deltaDay is then calculated once day-of-month adjustments have occurred.
   * If the first day-of-month is 31, change the first day-of-month to 30.
   * If the second day-of-month is 31, change the second day-of-month to 30.
   * If the first date is the last day of February, change the first day-of-month to 30.
   * If the second date is the last day of February and it is not the maturity date,
   * change the second day-of-month to 30.
   * <p>
   * The method {@link DayCount#getDayCountFraction(LocalDate, LocalDate)} will assume
   * that the second date is not the maturity date.
   * <p>
   * Also known as '30E/360 German' or 'German'.
   * Defined by the 2006 ISDA definitions 4.16h.
   */
  public static final DayCount THIRTY_E_360_ISDA = Standard.THIRTY_E_360_ISDA;
  /**
   * The '30E/360' day count, which treats input day-of-month 31 specially.
   * <p>
   * The result is calculated as {@code (360 * deltaYear + 30 * deltaMonth + deltaDay) / 360}.
   * The deltaDay is then calculated once day-of-month adjustments have occurred.
   * If the first day-of-month is 31, it is changed to 30.
   * If the second day-of-month is 31, it is changed to 30.
   * <p>
   * Also known as '30/360 ISMA', '30/360 European', '30S/360 Special German' or 'Eurobond'.
   * Defined by the 2006 ISDA definitions 4.16g and ICMA rule 251.1(ii) and 252.2.
   */
  public static final DayCount THIRTY_E_360 = Standard.THIRTY_E_360;
  /**
   * The '30E+/360' day count, which treats input day-of-month 31 specially.
   * <p>
   * The result is calculated as {@code (360 * deltaYear + 30 * deltaMonth + deltaDay) / 360}.
   * The deltaDay and deltaMonth are calculated once adjustments have occurred.
   * If the first day-of-month is 31, it is changed to 30.
   * If the second day-of-month is 31, it is changed to 1 and the second month is incremented.
   */
  public static final DayCount THIRTY_EPLUS_360 = Standard.THIRTY_EPLUS_360;

  /**
   * The extended enum lookup from name to instance.
   */
  private static final ExtendedEnum<DayCount> ENUM_LOOKUP = ExtendedEnum.of(DayCount.class);

  //-------------------------------------------------------------------------
  /**
   * Obtains a {@code BusinessDayConvention} from a unique name.
   * 
   * @param uniqueName  the unique name of the calendar
   * @return the holiday calendar
   */
  static DayCount of(String uniqueName) {
    ArgChecker.notNull(uniqueName, "uniqueName");
    return ENUM_LOOKUP.lookup(uniqueName);
  }

  //-------------------------------------------------------------------------
  /**
   * Restricted constructor.
   */
  private DayCounts() {
  }

  //-------------------------------------------------------------------------
  /**
   * Standard day count conventions.
   */
  static enum Standard implements DayCount {

    // always one
    ONE_ONE("1/1") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        return 1;
      }
    },
    // actual days / actual days in year
    ACT_ACT_ISDA("Act/Act ISDA") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        int y1 = firstDate.getYear();
        int y2 = secondDate.getYear();
        double firstYearLength = firstDate.lengthOfYear();
        if (y1 == y2) {
          double actualDays = secondDate.getDayOfYear() - firstDate.getDayOfYear();
          return actualDays / firstYearLength;
        }
        double firstRemainderOfYear = firstYearLength - firstDate.getDayOfYear() + 1;
        double secondRemainderOfYear = secondDate.getDayOfYear() - 1;
        double secondYearLength = secondDate.lengthOfYear();
        return firstRemainderOfYear / firstYearLength +
            secondRemainderOfYear / secondYearLength +
            (y2 - y1 - 1);
      }
    },
    // actual days / 365 or 366
    ACT_365_ACTUAL("Act/365 Actual") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        long actualDays = checkGetActualDays(firstDate, secondDate, scheduleInfo);
        LocalDate nextLeap = DateAdjusters.nextLeapDay(firstDate);
        return actualDays / (nextLeap.isAfter(secondDate) ? 365d : 366d);
      }
    },
    // simple actual days / 360
    ACT_360("Act/360") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        long actualDays = checkGetActualDays(firstDate, secondDate, scheduleInfo);
        return actualDays / 360d;
      }
    },
    // simple actual days / 364
    ACT_364("Act/364") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        long actualDays = checkGetActualDays(firstDate, secondDate, scheduleInfo);
        return actualDays / 364d;
      }
    },
    // simple actual days / 365
    ACT_365("Act/365") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        long actualDays = checkGetActualDays(firstDate, secondDate, scheduleInfo);
        return actualDays / 365d;
      }
    },
    // simple actual days / 365.25
    ACT_365_25("Act/365.25") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        long actualDays = checkGetActualDays(firstDate, secondDate, scheduleInfo);
        return actualDays / 365.25d;
      }
    },
    // no leaps / 365
    NL_365("NL/365") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        long actualDays = checkGetActualDays(firstDate, secondDate, scheduleInfo);
        int numberOfLeapDays = 0;
        LocalDate temp = DateAdjusters.nextLeapDay(firstDate);
        while (temp.isAfter(secondDate) == false) {
          numberOfLeapDays++;
          temp = DateAdjusters.nextLeapDay(temp);
        }
        return (actualDays - numberOfLeapDays) / 365d;
      }
    },
    // ISDA thirty day months / 360
    THIRTY_360_ISDA("30/360 ISDA") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        int d1 = firstDate.getDayOfMonth();
        int d2 = secondDate.getDayOfMonth();
        if (d1 == 31) {
          d1 = 30;
        }
        if (d2 == 31 && d1 == 30) {
          d2 = 30;
        }
        return thirty360(
            firstDate.getYear(), firstDate.getMonthValue(), d1,
            secondDate.getYear(), secondDate.getMonthValue(), d2);
      }
    },
    // US thirty day months / 360
    THIRTY_U_360("30U/360") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        int d1 = firstDate.getDayOfMonth();
        int d2 = secondDate.getDayOfMonth();
        boolean lastFeb1 = (firstDate.getMonthValue() == 2 && d1 == firstDate.lengthOfMonth());
        boolean lastFeb2 = (secondDate.getMonthValue() == 2 && d2 == secondDate.lengthOfMonth());
        if (scheduleInfo.isEndOfMonthConvention() && lastFeb1) {
          if (lastFeb2) {
            d2 = 30;
          }
          d1 = 30;
        }
        if (d1 == 31) {
          d1 = 30;
        }
        if (d2 == 31 && d1 == 30) {
          d2 = 30;
        }
        return thirty360(
            firstDate.getYear(), firstDate.getMonthValue(), d1,
            secondDate.getYear(), secondDate.getMonthValue(), d2);
      }
    },
    // ISDA EU thirty day months / 360
    THIRTY_E_360_ISDA("30E/360 ISDA") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        int d1 = firstDate.getDayOfMonth();
        int d2 = secondDate.getDayOfMonth();
        boolean lastFeb1 = (firstDate.getMonthValue() == 2 && d1 == firstDate.lengthOfMonth());
        boolean lastFeb2 = (secondDate.getMonthValue() == 2 && d2 == secondDate.lengthOfMonth());
        if (d1 == 31 || lastFeb1) {
          d1 = 30;
        }
        if (d2 == 31 || (lastFeb2 && !scheduleInfo.isScheduleEndDate(secondDate))) {
          d2 = 30;
        }
        return thirty360(
            firstDate.getYear(), firstDate.getMonthValue(), d1,
            secondDate.getYear(), secondDate.getMonthValue(), d2);
      }
    },
    // E thirty day months / 360
    THIRTY_E_360("30E/360") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        int d1 = firstDate.getDayOfMonth();
        int d2 = secondDate.getDayOfMonth();
        if (d1 == 31) {
          d1 = 30;
        }
        if (d2 == 31) {
          d2 = 30;
        }
        return thirty360(
            firstDate.getYear(), firstDate.getMonthValue(), d1,
            secondDate.getYear(), secondDate.getMonthValue(), d2);
      }
    },
    // E+ thirty day months / 360
    THIRTY_EPLUS_360("30E+/360") {
      @Override
      public double getDayCountFraction(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
        check(firstDate, secondDate, scheduleInfo);
        int d1 = firstDate.getDayOfMonth();
        int d2 = secondDate.getDayOfMonth();
        int m1 = firstDate.getMonthValue();
        int m2 = secondDate.getMonthValue();
        if (d1 == 31) {
          d1 = 30;
        }
        if (d2 == 31) {
          d2 = 1;
          m2 = m2 + 1;  // nature of calculation means no need to adjust Dec to Jan
        }
        return thirty360(
            firstDate.getYear(), m1, d1,
            secondDate.getYear(), m2, d2);
      }
    };

    // name
    private final String name;

    // create
    private Standard(String name) {
      this.name = name;
    }

    // calculate using the standard 30/360 function - 360(y2 - y1) + 30(m2 - m1) + (d2 - d1)) / 360
    private static double thirty360(int y1, int m1, int d1, int y2, int m2, int d2) {
      return (360 * (y2 - y1) + 30 * (m2 - m1) + (d2 - d1)) / 360d;
    }

    // validate inputs and return actual days difference
    private static long checkGetActualDays(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
      ArgChecker.notNull(firstDate, "firstDate");
      ArgChecker.notNull(secondDate, "secondDate");
      ArgChecker.notNull(scheduleInfo, "scheduleInfo");
      long actualDays = secondDate.toEpochDay() - firstDate.toEpochDay();
      ArgChecker.isTrue(actualDays >= 0, "Dates must be in order");
      return actualDays;
    }

    // validate inputs
    private static void check(LocalDate firstDate, LocalDate secondDate, ScheduleInfo scheduleInfo) {
      ArgChecker.notNull(firstDate, "firstDate");
      ArgChecker.notNull(secondDate, "secondDate");
      ArgChecker.notNull(scheduleInfo, "scheduleInfo");
      ArgChecker.isFalse(secondDate.isBefore(firstDate), "Dates must be in order");
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return name;
    }
  }

}
