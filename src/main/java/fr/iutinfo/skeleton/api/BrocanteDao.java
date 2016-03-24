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
	@SqlUpdate("create table brocante "
			+ "(id integer primary key autoincrement,"
			+ " libelle varchar(255),"
			+ " nomOrganisateur varchar(255),"
			+ " emailOrganisateur varchar(255),"
			+ " telOrganisateur varchar(255),"
			+ " pays varchar(255), "
			+ "departement integer, "
			+ "ville varchar(255), "
			+ "codePostal varchar(5), "
			+ "rue varchar(255) , "
			+ "date varchar(255), "
			+ "heure_debut varchar(5), "
			+ "heure_fin varchar(5), "
			+ "salle varchar(255), "
			+ "handicape boolean, "
			+ "prixEmplacement float)")
	void createBrocanteTable();

	@SqlUpdate("insert into brocante (libelle, nomOrganisateur, emailOrganisateur, telOrganisateur, pays, departement, ville, codePostal, rue, date, heure_debut, heure_fin, salle, handicape, prixEmplacement)"
		+ " values (:libelle, :nomOrganisateur, :emailOrganisateur, :telOrganisateur, :pays, :departement, :ville, :codePostal, :rue, :date, :heure_debut, :heure_fin, :salle, :handicape, :prixEmplacement)")
	@GetGeneratedKeys
	int insert(@BindBean() Brocante brocante);
	

	@SqlQuery("select * from brocante order by id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Brocante> all();
	
	@SqlUpdate("delete from brocante where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	void deleteBrocante(@Bind("id") int id);
	
	/*@SqlUpdate("update brocante set libelle = :brocante.libelle where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	void updateBrocante(@Bind("id") int id, @BindBean() Brocante brocante);*/

	@SqlQuery("select * from brocante where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	Brocante find(@Bind("id") int id);
	
	void close();

	
}
