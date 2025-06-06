package com.artemissoftware.calliopenotes.controllers.note.models

import jakarta.validation.constraints.NotBlank

data class NoteRequest(
    val id: String?,
    @field:NotBlank(message = "Title can't be blank.")
    val title: String,
    val content: String,
    val color: Long,
)
