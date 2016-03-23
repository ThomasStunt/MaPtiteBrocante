package fr.iutinfo.skeleton.api;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface BrocanteDao {
	@SqlUpdate("create table brocante (id integer primary key autoincrement, libelle varchar(100), ville varchar(100), addresse varchar(100), date varchar(9), heureD varchar(5), heureF(5), desc text, user_id integer , CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id))")
	void createBrocanteTable();

	@SqlUpdate("insert into brocante (libelle,ville,addresse,date,heureD,heureF,desc, user_id) values (:libelle, :ville, :addresse, :date, :heure_debut, :heure_fin, :desc: user_id)")
	@GetGeneratedKeys
	int insert(@BindBean() Brocante brocante);
	
	
	
}
