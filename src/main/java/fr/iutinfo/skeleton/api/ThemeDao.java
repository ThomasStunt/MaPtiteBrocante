package fr.iutinfo.skeleton.api;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface ThemeDao {

	@SqlUpdate("create table themes " + "(id integer primary key autoincrement," + " libelle varchar(255))")
	void createThemeTable();

	@SqlQuery("select * from themes order by id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Theme> all();

	@SqlUpdate("delete from themes where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	void deleteTheme(@Bind("id") int id);

	@SqlQuery("select * from themes where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	Theme find(@Bind("id") int id);

	@SqlUpdate("insert into themes (libelle)"
			+ " values (:libelle)")
	@GetGeneratedKeys
	int insert(@BindBean() Theme theme);

	void close();
}
