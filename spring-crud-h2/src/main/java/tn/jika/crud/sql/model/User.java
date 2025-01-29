package tn.jika.crud.sql.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

	@Id
	private String id;

	private String name;

	private String firstName;

	private String lastName;

	private String adress;

    @Enumerated(EnumType.STRING)
	private Sex sex;

	@Column(name = "mail")
	private String email;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<Car> cars;

	public void addCar(Car car) {
		if (cars == null) {
			cars = new ArrayList<>();
		}
		car.setOwner(this);
		cars.add(car);
	}

}
