package com.learning.platform.tools;

import org.flywaydb.core.Flyway;

/**
 * Standalone tool to run Flyway.repair() against the configured DB.
 *
 * Usage (from Eclipse):
 * - Run As -> Java Application (select this class)
 * - Optionally set environment variables DB_URL, DB_USER, DB_PASS
 *
 * Defaults mirror application.yml:
 * jdbc:postgresql://localhost:5432/learning_platform, user=postgres, pass=postgres
 */
public class FlywayRepairTool {
    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        if (url == null || url.isBlank()) {
            url = "jdbc:postgresql://localhost:5432/learning_platform";
        }
        String user = System.getenv("DB_USER");
        if (user == null || user.isBlank()) {
            user = "postgres";
        }
        String pass = System.getenv("DB_PASS");
        if (pass == null) {
            pass = "postgres";
        }

        System.out.println("Using DB URL: " + url);
        System.out.println("Using DB user: " + user);

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, pass)
                    .load();

            System.out.println("Running Flyway.repair()...");
            flyway.repair();
            System.out.println("Flyway.repair() completed successfully.");

            System.out.println("You can now restart your application.");
        } catch (Exception e) {
            System.err.println("Flyway repair failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
        }
    }
}
