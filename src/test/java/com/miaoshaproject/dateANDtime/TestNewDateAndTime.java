package com.miaoshaproject.dateANDtime;

import com.jayway.jsonpath.spi.mapper.TapestryMappingProvider;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TestNewDateAndTime {

    @Test
    public void testLocalDate() {

        //表示日期  年月日
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now);
        LocalDate of = LocalDate.of(2018, 8, 8);
        System.out.println("of = " + of);
        System.out.println("of.getYear() = " + of.getYear());
        System.out.println("of.getMonth() = " + of.getMonth());
        System.out.println("of.getDayOfWeek() = " + of.getDayOfWeek());
    }

    @Test
    public void testLocalTime() {
        LocalTime now = LocalTime.now();
        System.out.println("now = " + now);
        System.out.println("LocalTime.of(15, 15, 15) = " + LocalTime.of(15, 15, 15));
        System.out.println("now.getHour() = " + now.getHour());
        System.out.println("now.getSecond() = " + now.getSecond());
        System.out.println("now.getNano() （纳秒）= " + now.getNano());
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        LocalDateTime of = LocalDateTime.of(2000, 3, 3, 13, 13, 13);
        System.out.println("of = " + of);
        System.out.println("of.getHour() = " + of.getHour());

        //修改时间with……()  修改后返回新的时间 原时间不会受到影响
        LocalDateTime localDateTime = of.withMinute(59);
        System.out.println("of = " + of);
        System.out.println("localDateTime = " + localDateTime);

        //增加或者减少时间plus……() minus……()
        LocalDateTime localDateTime1 = of.plusSeconds(12);
        System.out.println("localDateTime1 = " + localDateTime1); //13 +12 = 25
        LocalDateTime localDateTime2 = of.minusMonths(1);
        System.out.println("localDateTime2 = " + localDateTime2);

        //比较时间 isAfter() isBefore() isEqual() 返回Boolean值
        boolean after = localDateTime2.isAfter(localDateTime1);
        System.out.println("after = " + after);
        System.out.println(localDateTime.isEqual(localDateTime2));
    }

    @Test
    public void testDateTimeFormat() {
        LocalDateTime now = LocalDateTime.now();

        //格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        String format = now.format(dateTimeFormatter);
        System.out.println("format = " + format);

        //自定义时间格式
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String format1 = now.format(dateTimeFormatter1);
        System.out.println("format1 = " + format1);

        //解析（线程安全） 反格式化获取LocalDateTime
        for (int i = 0; i < 20; i++) {

            new Thread(() -> {

                LocalDateTime parse = LocalDateTime.parse("2023年03月28日 21:40:35", dateTimeFormatter1);
                System.out.println("parse = " + parse);
            }).start();
        }
    }

    @Test
    public void testInstant() {

        //时间戳 Instant内部 保存了秒和纳秒 一般不是给用户用的 而是方便我们程序做一些统计
        Instant now = Instant.now();
        System.out.println("now = " + now); //2023-03-28T13:45:04.638Z
        System.out.println("now.getEpochSecond() = " + now.getEpochSecond()); //从1970-01-01T00:00:00Z 开始的秒数
        System.out.println("now.getNano() = " + now.getNano()); //纳秒
    }

    @Test
    public void testTemporalAdjuster() {

        LocalDateTime now = LocalDateTime.now();
        //将日期调整到下个月第一天 使用自定义的时间调整器
        TemporalAdjuster nextMonthFirstDay = (temporal) -> {
            LocalDateTime localDateTime = (LocalDateTime) temporal;
            return localDateTime.plusMonths(1).withDayOfMonth(1);
        };
        System.out.println("now.with(nextMonthFirstDay) = " + now.with(nextMonthFirstDay));

        //使用jdk自带的时间调整器
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.lastDayOfMonth();
        System.out.println("这个月底：" + now.with(temporalAdjuster));
    }

    @Test
    public void testZoned() {

        //获取所有的时区ID
        ZoneId.getAvailableZoneIds().forEach(System.out::println);

        //操作带时区的类
        ZonedDateTime now = ZonedDateTime.now(Clock.systemUTC()); //世界标准时间
        System.out.println("now = " + now);

        ZonedDateTime hkTime = ZonedDateTime.now(ZoneId.of("Asia/Hong_Kong")); //香港时间
        System.out.println("hkTime = " + hkTime);

        //修改时区 withZoneSameInstant 既更改时区又更改时间
        //withZoneSameLocal 只更改时区不更改时间
        ZonedDateTime zonedDateTime = hkTime.withZoneSameInstant(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zonedDateTime1 = hkTime.withZoneSameLocal(ZoneId.of("Europe/Tallinn"));
        System.out.println("Tallinn time = " + zonedDateTime);
        System.out.println("Local time = " + zonedDateTime1);
    }

    @Test
    public void testDurationAndPeriod() {

        //Duration 计算时间的距离
        LocalTime now = LocalTime.now();
        LocalTime of = LocalTime.of(15, 15, 15);
        Duration between = Duration.between(of, now);
        System.out.println(between.toMinutes()); //查看相差多少分钟
        System.out.println(between.toDays()); //查看相差多少天


        //Period 计算相差的时间
        LocalDate now1 = LocalDate.now();
        LocalDate of1 = LocalDate.of(1999, 1, 1);
        Period between1 = Period.between(of1, now1);
        System.out.println("between1.getDays() = " + between1.getDays()); // 相差的天数
        System.out.println("between1.getYears() = " + between1.getYears()); //相差的年数
    }
}
