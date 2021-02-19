package fr.campus.academy.worldvisit.bdd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
class CountryDTO (

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val flag: String,
    val capital: String,
    val continent: String,
    val date: String?

) {
    override fun toString(): String {
        return "CountryDTO(id=$id, name='$name', flag='$flag', capital='$capital', continent='$continent', date=$date)"
    }
}
