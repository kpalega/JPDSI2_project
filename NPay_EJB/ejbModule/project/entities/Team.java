package project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the team database table.
 * 
 */
@Entity
@NamedQuery(name="Team.findAll", query="SELECT t FROM Team t")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idteam;

	private String name;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="team")
	private List<Payment> payments;

	//bi-directional many-to-one association to Mediaservice
	@ManyToOne
	@JoinColumn(name="idmediaservice")
	private Mediaservice mediaservice;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="teams")
	private List<User> users;

	public Team() {
	}

	public int getIdteam() {
		return this.idteam;
	}

	public void setIdteam(int idteam) {
		this.idteam = idteam;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setTeam(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setTeam(null);

		return payment;
	}

	public Mediaservice getMediaservice() {
		return this.mediaservice;
	}

	public void setMediaservice(Mediaservice mediaservice) {
		this.mediaservice = mediaservice;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}