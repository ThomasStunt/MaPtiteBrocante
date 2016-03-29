package fr.iutinfo.skeleton.api;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface ArticleDao {
	
	@SqlUpdate("create table articles "
			+ "(id integer primary key autoincrement,"
			+ " titre varchar(255),"
			+ " theme varchar(255),"
			+ " texte text)")
	void createArticleTable();
	
	@SqlQuery("select * from articles order by id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Article> all();
	
	@SqlUpdate("delete from articles where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	void deleteArticle(@Bind("id") int id);
	
	@SqlQuery("select * from articles where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	Article find(@Bind("id") int id);
	
	@SqlUpdate("insert into articles (titre, theme, texte)"
			+ " values (:titre, :theme, :texte)")
	@GetGeneratedKeys
	int insert(@BindBean() Article article);
	
	void close();
}
