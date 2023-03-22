
package acme.entities.audits;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank()
	@Length(max = 75)
	protected String			subject;

	@NotBlank()
	@Length(max = 100)
	protected String			assessment;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				finishDate;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Audit				audit;

	@NotBlank()
	@Pattern(regexp = "^A\\+?|B|C|F-?$")
	protected String			mark;

	@URL
	protected String			link;
}
