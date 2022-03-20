package database;

import java.util.*;

public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for(String role : Roles.ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }

        rolesRights.get(Roles.ADMINISTRATOR).addAll(Arrays.asList(Rights.CREATE_EMPLOYEE, Rights.READ_EMPLOYEE,
                Rights.UPDATE_EMPLOYEE, Rights.DELETE_EMPLOYEE, Rights.GENERATE_REPORTS));

        rolesRights.get(Roles.EMPLOYEE).addAll(Arrays.asList(Rights.ADD_CLIENT, Rights.UPDATE_CLIENT, Rights.VIEW_CLIENT,
                Rights.DELETE_CLIENT, Rights.CREATE_ACCOUNT, Rights.UPDATE_ACCOUNT, Rights.DELETE_ACCOUNT,
                Rights.TRANSFER_MONEY, Rights.PROCESS_BILLS));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[] {ADMINISTRATOR, EMPLOYEE};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String EVENT = "event";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[] {USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                CLIENT, ACCOUNT, EVENT};
    }

    public static class Rights {
        public static final String ADD_CLIENT = "add_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String READ_EMPLOYEE = "read_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String GENERATE_REPORTS = "generate_reports";

        public static final String[] RIGHTS = {ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, DELETE_CLIENT, CREATE_ACCOUNT,
                UPDATE_ACCOUNT, DELETE_ACCOUNT, TRANSFER_MONEY,
                PROCESS_BILLS, CREATE_EMPLOYEE, READ_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE, GENERATE_REPORTS};
    }

    public static class AccountTypes {
        public static final String VISA = "visa";
        public static final String MASTERCARD = "mastercard";

        public static final String[] TYPES = {VISA, MASTERCARD};
    }

}
