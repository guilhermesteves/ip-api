package models.common.constants;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 *
 * All messages delivered to front-end
 */
public class MESSAGES {

    // TODO: PUT ALL IN A RESOURCE BUNDLE

    public static final String UNAUTHORIZED_403 = "Você não pode passar. \\o/";
    public static final String NOT_FOUND_404 = "\"Você poderia me dizer, por favor, que caminho devo tomar para sair daqui?\"\n" +
            "\"Isso depende muito de para onde você quer chegar.\"\n" +
            "\"Eu não ligo muito para onde -\"\n" +
            "\"Então não importa que caminho você vai.\"\n" +
            "― Lewis Carroll, Alice no país das maravilhas";


    public static final String MISSING_INFO = "Informações incompletas";


    public static final String INCORRECT_PASSWORD = "Senha incorreta";
    private static final String _NOT_FOUND = " não encontrado";
    private static final String ALREADY_ = "Já existe um ";
    private static final String _EXISTS = " cadastrado";
    private static final String _WITH_THESE_ = " com esse ";
    private static final String EMAIL = "email";
    private static final String NAME = "nome";

    public static class SETTING {

        private static final String ADMIN = "Configuração";

        public static final String NOT_FOUND = ADMIN + _NOT_FOUND;
    }

    public static class MOD {

        public static final String MOD = "Moderador";

        public static final String NOT_FOUND = MOD + _NOT_FOUND;
        public static final String EMAIL_ALREADY_EXISTS = ALREADY_ + MOD.toLowerCase() + _EXISTS + _WITH_THESE_ + EMAIL;

        public static final String MOD_CANT = "Essa ação só pode ser feita por um " + MOD + " Global";
        public static final String GLOBAL_CANT_DELETE_GLOBAL = "Um " + MOD + " Global só pode ser deletado por um " + ADMIN.ADMIN;
        public static final String DO_NOT_HAVE_BOARD = "Você não é um " + MOD + " Global e nem tem acesso a essa board";
    }

    public static class ADMIN {

        private static final String ADMIN = "Administrador";

        public static final String NOT_FOUND = ADMIN + _NOT_FOUND;
        public static final String NOT_FOUND_BY_NAME = ALREADY_ + ADMIN.toLowerCase() + _EXISTS + _WITH_THESE_ + NAME;
        public static final String EMAIL_ALREADY_EXISTS = ALREADY_ + ADMIN.toLowerCase() + _EXISTS + _WITH_THESE_ + EMAIL;

        public static final String ADMIN_CANT_SUPER_ADMIN = "Você é um " + ADMIN + " e não pode realizar Ações em Super Administradores";
    }

    public static class BOARD {

        private static final String BOARD = "Board";

        public static final String NOT_FOUND = BOARD + _NOT_FOUND;
        public static final String CATALOG_NOT_FOUND = "Catálogo para essa" + BOARD + _NOT_FOUND;
        public static final String SLUG_ALREADY_EXISTS = ALREADY_ + BOARD.toLowerCase() + _EXISTS + _WITH_THESE_ + "slug";
    }
}
