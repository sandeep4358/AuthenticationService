package com.javafruit.AuthenticationService.utility;

public enum RoleEnum {

    CORPORATE_USER_LEV_1 {
        @Override
        String printInfo() {
            return "ADMIN";
        }
    }, CORPORATE_USER_LEV_2 {
        @Override
        String printInfo() {
            return "SUB_ADMIN";
        }
    }, CORPORATE_USER_LEV_3 {
        @Override
        String printInfo() {
            return "SUB_SUB_ADMIN";
        }
    };

    String level;

    abstract String printInfo();

}
