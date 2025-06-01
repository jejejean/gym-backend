package com.gym.shared.interfaces;

import java.util.Optional;

@FunctionalInterface
public interface LastNumberProvider {
    Optional<String> findLastNumber();
}
