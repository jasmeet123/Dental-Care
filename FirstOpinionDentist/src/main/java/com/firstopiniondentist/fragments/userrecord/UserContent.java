package com.firstopiniondentist.fragments.userrecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class UserContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<UserItem> ITEMS = new ArrayList<UserItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, UserItem> ITEM_MAP = new HashMap<String, UserItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createUserItem(i));
        }
    }

    private static void addItem(UserItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static UserItem createUserItem(int position) {
        return new UserItem("Jack", "Item " + position, new byte[100]);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class UserItem {
        public  String name;
        public  String detail;
        public  byte [] image;

        public UserItem(String name, String detail, byte [] image) {
            this.name = name;
            this.detail = detail;
            this.image = image;
        }

        @Override
        public String toString() {
            return detail;
        }
    }
}
