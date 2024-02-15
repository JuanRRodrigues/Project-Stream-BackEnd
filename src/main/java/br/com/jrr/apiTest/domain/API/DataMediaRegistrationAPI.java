package br.com.jrr.apiTest.domain.API;

import br.com.jrr.apiTest.domain.Enums.Category;
import jakarta.validation.constraints.NotBlank;

public record DataMediaRegistrationAPI(
        String title,
        Category category) {


}
