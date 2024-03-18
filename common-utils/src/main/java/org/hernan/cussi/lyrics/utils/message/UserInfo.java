package org.hernan.cussi.lyrics.utils.message;

import lombok.NonNull;

import java.util.Date;

public record UserInfo(@NonNull String name, @NonNull String email, @NonNull Date timestamp) {
}
