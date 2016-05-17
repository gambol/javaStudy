package gambol.examples.writecode.prettycode;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeRangeSet;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhenbao.zhou on 16/5/10.
 */
public class IfKiller4 {

    final static Splitter splitter1 = Splitter.on(",");
    final static Splitter splitter2 = Splitter.on("-");

    // request: name=如家&&area=北京,上海&&price=10-20,50-100
    List<Hotel> filter(final List<Hotel> hotelList, final Map<String, String> request) {
        List<Hotel> hotels = Lists.newArrayList();
        String name = request.get("name");
        Set<String> areas = Sets.newTreeSet(splitter1.split(request.get("area")));
        RangeSet<Integer> rangeSet = TreeRangeSet.create();

        for (String priceString : splitter1.splitToList(request.get("price"))) {
            List<String> strings = splitter2.splitToList(priceString);
            int low = Ints.tryParse(strings.get(0));
            int high = Ints.tryParse(strings.get(1));
            rangeSet.add(Range.openClosed(low, high));
        }

        Iterator<Hotel> it = hotelList.iterator();
        while (it.hasNext()) {
            Hotel hotel = it.next();
            if (!name.isEmpty() && !hotel.getName().contains(name)) {
                continue;
            }

            if (!areas.isEmpty() && !areas.contains(hotel.getArea())) {
                continue;
            }

            if (!rangeSet.isEmpty() && !rangeSet.contains(hotel.getPrice())) {
                continue;
            }

            // .... 还有其他过滤条件

            hotels.add(hotel);
        }

        return hotels;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class InputFiled {
        Hotel hotel;
        Map<String, String> requestMap;
    }

    static class AreaPredicate implements Predicate<InputFiled> {

        final static Splitter COMMA_SPLITTER = Splitter.on(",");

        @Override
        public boolean apply(InputFiled input) {
            Set<String> areas = Sets.newTreeSet(COMMA_SPLITTER.split(input.getRequestMap().get("area")));

            return areas.isEmpty() || areas.contains(input.getHotel().getArea());
        }
    }

    static class PricePredicate implements Predicate<InputFiled> {

        final static Splitter LINE_SPLITTER = Splitter.on("-");
        final static Splitter COMMA_SPLITTER = Splitter.on(",");

        @Override
        public boolean apply(InputFiled input) {
            RangeSet<Integer> rangeSet = TreeRangeSet.create();

            for (String priceString : COMMA_SPLITTER.splitToList(input.getRequestMap().get("price"))) {
                List<String> strings = LINE_SPLITTER.splitToList(priceString);
                int low = Ints.tryParse(strings.get(0));
                int high = Ints.tryParse(strings.get(1));
                rangeSet.add(Range.openClosed(low, high));
            }

            return rangeSet.isEmpty() || rangeSet.contains(input.getHotel().getPrice());
        }
    }

    static class NamePredicate implements Predicate<InputFiled> {
        @Override
        public boolean apply(InputFiled input) {
            String name = input.getRequestMap().get("name");

            return Strings.isNullOrEmpty(name) || input.getHotel().getName().contains(name);
        }
    }

    /**
     * 新增Predicate
     */
    static class XXPredicate implements Predicate<InputFiled> {
        @Override public boolean apply(InputFiled input) {
            return false;
        }
    }

    List<Hotel> filter2(final List<Hotel> hotelList, final Map<String, String> request) {
        Predicate<InputFiled> predicate = Predicates
                .and(new NamePredicate(),
                        new AreaPredicate(),
                        new PricePredicate(),
                        new XXPredicate());

        List<Hotel> filteredHotels = Lists.newArrayList();
        for (Hotel hotel : hotelList) {
            InputFiled input = new InputFiled(hotel, request);
            if (predicate.apply(input)) {
                filteredHotels.add(hotel);
            }
        }

        return filteredHotels;

    }

    @Data
    @AllArgsConstructor
    @ToString
    static class Hotel {
        String name;
        String area;
        int price;
    }

    public static void main(String[] args) {
        List<Hotel> hotelList = ImmutableList.of(new Hotel("如家", "北京", 300), new Hotel("七天", "北京", 200), new Hotel(
                "如家", "纽约", 20));

        Map<String, String> requestMap = Maps.newHashMap();
        requestMap.put("name", "如家");
        requestMap.put("area", "上海,北京");
        requestMap.put("price", "100-200,240-400");

        IfKiller4 killer = new IfKiller4();
        List<Hotel> filteredHotel = killer.filter(hotelList, requestMap);
        for (Hotel hotel : filteredHotel) {
            System.out.println("hotel:" + hotel);
        }

        filteredHotel = killer.filter2(hotelList, requestMap);
        for (Hotel hotel : filteredHotel) {
            System.out.println("hotel2:" + hotel);
        }

    }
}
