package fr.campus.academy.worldvisit.bdd

import androidx.room.*

@Dao
abstract class CountryDAO
{
    @Query("SELECT * FROM country ORDER BY date DESC")
    abstract fun findAll(): MutableList<CountryDTO>

    @Query("SELECT * FROM country WHERE name = :name")
    abstract fun findOneByName(name: String): CountryDTO

    @Insert
    abstract fun create(vararg country: CountryDTO)
    
    @Delete
    abstract fun delete(country: CountryDTO)
}