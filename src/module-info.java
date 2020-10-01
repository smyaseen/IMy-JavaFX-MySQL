module Imy{

    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;

    opens org.smy.imy;
    opens org.smy.imy.controller;
    opens org.smy.imy.view;
    opens org.smy.imy.util;
    opens org.smy.imy.model;
    opens org.smy.imy.datasource;
    opens org.smy.imy.authentication;

}