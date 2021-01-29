package project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iduser;

	private String email;

	private String fname;

	private String password;

	@Lob
	private String role;

	//bi-directional many-to-one association to Bannedmonth
	@OneToMany(mappedBy="user")
	private List<Bannedmonth> bannedmonths;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="user")
	private List<Payment> payments;

	//bi-directional many-to-many association to Team
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="user_has_team"
		, joinColumns={
			@JoinColumn(name="iduser")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idteam")
			}
		)
	private List<Team> teams;

	public User() {
	}

	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Bannedmonth> getBannedmonths() {
		return this.bannedmonths;
	}

	public void setBannedmonths(List<Bannedmonth> bannedmonths) {
		this.bannedmonths = bannedmonths;
	}

	public Bannedmonth addBannedmonth(Bannedmonth bannedmonth) {
		getBannedmonths().add(bannedmonth);
		bannedmonth.setUser(this);

		return bannedmonth;
	}

	public Bannedmonth removeBannedmonth(Bannedmonth bannedmonth) {
		getBannedmonths().remove(bannedmonth);
		bannedmonth.setUser(null);

		return bannedmonth;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setUser(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setUser(null);

		return payment;
	}

	public List<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	

}