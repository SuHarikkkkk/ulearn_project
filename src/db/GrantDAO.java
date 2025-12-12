package db;

import model.Grant;

import java.sql.*;
import java.util.List;

public class GrantDAO {

    private final Connection conn;

    public GrantDAO(Connection conn) {
        this.conn = conn; // получаем подключение к базе
    }

 //метод записывает ¬—≈ данные из списка в Ѕƒ
    public void saveAll(List<Grant> grants) throws SQLException {
        for (Grant g : grants) {
            int businessId = insertBusiness(g);
            int fiscalYearId = insertFiscalYear(g);

            insertGrant(g, businessId, fiscalYearId);
        }
    }

     //вставка бизнеса в таблицу businesses.
    private int insertBusiness(Grant g) throws SQLException {
        String select = "SELECT id FROM businesses WHERE company_name = ?";

        try (PreparedStatement ps = conn.prepareStatement(select)) {
            ps.setString(1, g.getCompanyName());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // бизнес уже существует
            }
        }

        // бизнеса нет Ч создаЄм новый
        String insert = """
            INSERT INTO businesses (company_name, street_name, business_type)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, g.getCompanyName());
            ps.setString(2, g.getStreetName());
            ps.setString(3, g.getBusinessType());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }

        return -1;
    }

    // создаЄт или находит год (таблица fiscal_years).
    private int insertFiscalYear(Grant g) throws SQLException {
        if (g.getFiscalYear() == null) return -1;

        String select = "SELECT id FROM fiscal_years WHERE year = ?";
        try (PreparedStatement ps = conn.prepareStatement(select)) {
            ps.setInt(1, g.getFiscalYear());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }

        String insert = "INSERT INTO fiscal_years (year) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, g.getFiscalYear());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }

        return -1;
    }

//записывает грант Ч св€зь бизнес + год + сумма + рабочие места.

    private void insertGrant(Grant g, int businessId, int fiscalYearId) throws SQLException {
        String sql = """
            INSERT INTO grants (business_id, fiscal_year_id, amount, jobs_created)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, businessId);
            ps.setInt(2, fiscalYearId);
            ps.setDouble(3, g.getGrantAmount());
            ps.setObject(4, g.getJobsCreated()); // позвол€ет вставл€ть null
            ps.executeUpdate();
        }
    }
}
