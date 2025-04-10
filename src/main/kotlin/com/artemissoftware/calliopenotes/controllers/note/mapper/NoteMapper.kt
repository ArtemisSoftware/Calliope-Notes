package com.artemissoftware.calliopenotes.controllers.note.mapper

import com.artemissoftware.calliopenotes.controllers.note.models.NoteRequest
import com.artemissoftware.calliopenotes.controllers.note.models.NoteResponse
import com.artemissoftware.calliopenotes.database.model.Note
import org.bson.types.ObjectId
import java.time.Instant

internal fun Note.toResponse(): NoteResponse {
    return NoteResponse(
        id = id.toHexString(),
        title = title,
        content = content,
        color = color,
        createdAt = createdAt
    )
}

internal fun NoteRequest.toNote(ownerId: String): Note {
    return Note(
        id = id?.let { ObjectId(it) } ?: ObjectId.get(),
        title = title,
        content = content,
        color = color,
        createdAt = Instant.now(),
        ownerId = ObjectId(/*ownerId*/)
    )
}