package project.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bannedmonth database table.
 * 
 */
@Entity
@NamedQuery(name="Bannedmonth.findAll", query="SELECT b FROM Bannedmonth b")
public class Bannedmonth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idbannedMonth;

	private String month1;

	private String month2;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="iduser")
	private User user;

	public Bannedmonth() {
	}

	public int getIdbannedMonth() {
		return this.idbannedMonth;
	}

	public void setIdbannedMonth(int idbannedMonth) {
		this.idbannedMonth = idbannedMonth;
	}

	public String getMonth1() {
		return this.month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getMonth2() {
		return this.month2;
	}

	public void setMonth2(String month2) {
		this.month2 = month2;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}