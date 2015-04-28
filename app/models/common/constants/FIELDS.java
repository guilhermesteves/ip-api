package models.common.constants;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All JSON fields used to send to front-end
 */
public class FIELDS {

    private static final String DEFAULT_ID = "id";

    public static class BASE_USER {

        public static final String ID = DEFAULT_ID;
        public static final String DATE_CREATED = "date_created";
        public static final String ACTIVE = "active";

        public static final String LOYALTY = "loyalty";

        public static class STAFF {
            
            public static final String EMAIL = "email";
            public static final String NAME = "name";
            public static final String PASSWORD = "password";

            public static final String NEW_PASSWORD = "newPassword";
            public static final String OLD_PASSWORD = "oldPassword";

            public static class MOD {

                public static final String MODERATING_BOARDS = "moderating_boards";
                public static final String ACCESS_LEVEL = "access_level";
            }

            public static class ADMIN {

                public static final String IS_SUPER_ADMIN = "super_admin";
            }
        }

        public static class USER {

            public static final String VISUAL_ID = "visual_id";
            public static final String KARMA = "karma";
            public static final String IDENTIFIER = "identifier";
        }
    }

    public static class LOGIN {
        public static final String NAME = "name";
        public static final String PASSWORD = "password";

        public static final String IDENTIFIER = "identifier";
    }

    public static class ENUM {

        public static final String ID = DEFAULT_ID;
        public static final String DESCRIPTION = "description";

        public static class MOD_LEVEL {
            public static final String LEVEL = "level";
        }
    }

    public static class BOARD {
        public static final String ID = DEFAULT_ID;

        public static final String ACTIVE = "active";
        public static final String NAME = "name";

        public static final String SLUG = "slug";
        public static final String LONG_SLUG = "long_slug";

        public static final String MAX_THREADS = "max_threads";
        public static final String HAS_CATALOG = "has_catalog";

    }

    public static class SETTINGS {
        public static final String ID = DEFAULT_ID;

        public static final String KEY = "key";
        public static final String VALUE = "value";
        public static final String TYPE = "type";
    }

}
