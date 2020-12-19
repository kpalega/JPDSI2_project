package project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the mediaservice database table.
 * 
 */
@Entity
@NamedQuery(name="Mediaservice.findAll", query="SELECT m FROM Mediaservice m")
public class Mediaservice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idmediaservice;

	private float prize;

	private String service;

	//bi-directional many-to-one association to Team
	@OneToMany(mappedBy="mediaservice")
	private List<Team> teams;

	public Mediaservice() {
	}

	public int getIdmediaservice() {
		return this.idmediaservice;
	}

	public void setIdmediaservice(int idmediaservice) {
		this.idmediaservice = idmediaservice;
	}

	public float getPrize() {
		return this.prize;
	}

	public void setPrize(float prize) {
		this.prize = prize;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Team addTeam(Team team) {
		getTeams().add(team);
		team.setMediaservice(this);

		return team;
	}

	public Team removeTeam(Team team) {
		getTeams().remove(team);
		team.setMediaservice(null);

		return team;
	}

}