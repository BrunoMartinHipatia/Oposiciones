package com.example.oposiciones.di




import android.app.Application
import androidx.room.Room
import com.example.oposiciones.data.ExamenesDatabse
import com.example.oposiciones.data.ExamenesRepository
import com.example.oposiciones.data.ExamenesRepositoryImpl
import com.example.oposiciones.data.ExamenesResultadosRepository
import com.example.oposiciones.data.ExamenesResultadosRepositoryImpl
import com.example.oposiciones.data.ResultadosExamenes
import com.example.oposiciones.data.ResultadosExamenesDatabse

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConverterDatabase(app:Application) : ExamenesDatabse {
         return Room.databaseBuilder(
             app,
             ExamenesDatabse::class.java,
             "examenes_data_database"
         ).build()
    }

    @Provides
    @Singleton
    fun provideConverterResultadosDatabase(app:Application) : ResultadosExamenesDatabse {
        return Room.databaseBuilder(
            app,
            ResultadosExamenesDatabse::class.java,
            "examenes_resultados_data_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideConverterResultadosRepository(db : ResultadosExamenesDatabse) : ExamenesResultadosRepository
    {
        return ExamenesResultadosRepositoryImpl(db.resultadosExamenesDao)
    }

    @Provides
    @Singleton
    fun provideConverterRepository(db : ExamenesDatabse) : ExamenesRepository
    {
       return ExamenesRepositoryImpl(db.converterDAO)
    }

}