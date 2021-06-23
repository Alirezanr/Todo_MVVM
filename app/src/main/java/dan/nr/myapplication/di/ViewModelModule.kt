package dan.nr.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.ui.authentication.AuthViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule
{
    @Singleton
    @Provides
    fun provideAuthViewModel(repository: AuthRepository): AuthViewModel = AuthViewModel(repository)
}