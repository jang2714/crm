package kr.code.main.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serializable;
import java.util.Locale;

/**
 * Entity 생성시 변수의 이름을
 * 그대로 쓰기 위해서 설정
 * 실제 엔티티 명과 테이블 명이 그대로 매핑된다.
 * 따로 변수 위에
 * 어노테이션으로 컬럼패빙하지 않아도 됨
 *
 */
public class PhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl implements Serializable {

	private static final long serialVersionUID = 1354329537409769076L;

	public static final PhysicalNamingStrategyImpl INSTANCE = new PhysicalNamingStrategyImpl();

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    protected static String addUnderscores(String name) {
        final StringBuilder buf = new StringBuilder( name.replace('.', '_') );
        for (int i=1; i<buf.length()-1; i++) {
            if (
                Character.isLowerCase( buf.charAt(i-1) ) &&
                Character.isUpperCase( buf.charAt(i) ) &&
                Character.isLowerCase( buf.charAt(i+1) )
            ) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toLowerCase(Locale.ROOT);
    }

}