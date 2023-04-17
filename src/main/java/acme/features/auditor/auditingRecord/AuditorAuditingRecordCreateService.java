
package acme.features.auditor.auditingRecord;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit audit;
		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(masterId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final AuditingRecord object;
		int masterId;
		Audit audit;
		masterId = super.getRequest().getData("masterId", int.class);
		audit = this.repository.findOneAuditById(masterId);
		object = new AuditingRecord();
		object.setSubject("");
		object.setAssessment("");
		object.setStartDate(null);
		object.setFinishDate(null);
		object.setMark("");
		object.setCorrection(false);
		object.setAudit(audit);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;
		super.bind(object, "subject", "assessment", "startDate", "finishDate", "mark", "link", "correction");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
		final Duration duration = MomentHelper.computeDuration(object.getStartDate(), object.getFinishDate());
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfter(object.getFinishDate(), object.getStartDate()), "endPeriod", "auditor.auditingRecord.form.error.endPeriod-too-soon");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(duration.compareTo(Duration.ofHours(1)) >= 0, "endPeriod", "auditor.auditingRecord.form.error.endPeriod-too-short");

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		this.repository.save(object);
	}
	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "startDate", "finishDate", "mark", "link", "correction");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getAudit().isDraftMode());
		super.getResponse().setData(tuple);
	}
}
