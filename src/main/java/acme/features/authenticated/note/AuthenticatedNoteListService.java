
package acme.features.authenticated.note;

import org.springframework.stereotype.Service;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

}
