package com.readin.component.Security;

import java.lang.String;

public class  Credential {
        private String username;
        private String password;

        public String getName() {
            return username;
        }

        public void setName(String name) {
            this.username = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
