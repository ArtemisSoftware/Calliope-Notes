package com.artemissoftware.calliopenotes.controllers.note

import com.artemissoftware.calliopenotes.controllers.note.mapper.toNote
import com.artemissoftware.calliopenotes.controllers.note.mapper.toResponse
import com.artemissoftware.calliopenotes.controllers.note.models.NoteRequest
import com.artemissoftware.calliopenotes.controllers.note.models.NoteResponse
import com.artemissoftware.calliopenotes.database.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notes")
class NoteController(
    private val repository: NoteRepository,
) {

    @PostMapping
    fun save(
        /*@Valid*/ @RequestBody body: NoteRequest
    ): NoteResponse {
        val ownerId = "1"//SecurityContextHolder.getContext().authentication.principal as String
        val note = repository.save(body.toNote(ownerId))
        return note.toResponse()
    }

    @GetMapping
    fun findByOwnerId(): List<NoteResponse> {
        val ownerId = "1"//SecurityContextHolder.getContext().authentication.principal as String
        return repository.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val note = repository.findById(ObjectId(id)).orElseThrow {
            IllegalArgumentException("Note not found")
        }
        //val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        //if(note.ownerId.toHexString() == ownerId) {
            repository.deleteById(ObjectId(id))
        //}
    }
}