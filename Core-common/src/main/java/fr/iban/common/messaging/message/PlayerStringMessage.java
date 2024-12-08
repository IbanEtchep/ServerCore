package fr.iban.common.messaging.message;

import java.util.UUID;

public record PlayerStringMessage(UUID uuid, String string) {}
