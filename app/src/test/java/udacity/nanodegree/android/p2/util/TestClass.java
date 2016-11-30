package udacity.nanodegree.android.p2.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alexandre on 26/11/2016.
 */

@Table(name = "table_test")
public class TestClass {

    private String nocolumn1;

    @Id
    @Column
    private String string_column1;

    @Column(unique = true, nullable = false)
    private Integer integer_column;

    @Column(name = "double_column")
    private Double double_column;

    @Column
    private Date date_column;

    private String nocolumn2;

    public String getNocolumn1() {
        return nocolumn1;
    }

    public void setNocolumn1(String nocolumn1) {
        this.nocolumn1 = nocolumn1;
    }

    public String getString_column1() {
        return string_column1;
    }

    public void setString_column1(String string_column1) {
        this.string_column1 = string_column1;
    }

    public Integer getInteger_column() {
        return integer_column;
    }

    public void setInteger_column(Integer integer_column) {
        this.integer_column = integer_column;
    }

    public Double getDouble_column() {
        return double_column;
    }

    public void setDouble_column(Double double_column) {
        this.double_column = double_column;
    }

    public Date getDate_column() {
        return date_column;
    }

    public void setDate_column(Date date_column) {
        this.date_column = date_column;
    }

    public String getNocolumn2() {
        return nocolumn2;
    }

    public void setNocolumn2(String nocolumn2) {
        this.nocolumn2 = nocolumn2;
    }
}
