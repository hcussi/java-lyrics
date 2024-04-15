package org.hernan.cussi.lyrics.utils.message;

import lombok.NonNull;

import java.util.Date;

public record UserInfo(@NonNull String name, String email, @NonNull String originalEmail, @NonNull String password, @NonNull Date timestamp) {
}
