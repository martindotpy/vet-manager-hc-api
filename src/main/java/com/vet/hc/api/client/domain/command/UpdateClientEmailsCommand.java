package com.vet.hc.api.client.domain.command;

import java.util.Set;

import com.vet.hc.api.client.domain.model.ClientEmail;
import com.vet.hc.api.shared.domain.command.Command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command to update the client emails'.
 *
 * <p>
 * If an email is removed from the set, it will be deleted from the database.
 * </p>
 * <p>
 * If an email is added to the set without an ID, it will be inserted into the
 * database.
 * </p>
 * <p>
 * If an email is added to the set with an ID, it will be updated in the
 * database.
 * </p>
 * <p>
 * If the client has no emails, the emails set will be empty.
 * </p>
 */
@Getter
@Builder
public class UpdateClientEmailsCommand implements Command {
    private Set<ClientEmail> emails;
}
