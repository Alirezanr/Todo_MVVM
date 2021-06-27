package dan.nr.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.base.BaseRepository
import dan.nr.myapplication.base.BaseViewModel
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.util.UserPreferences
import dan.nr.myapplication.viewmodel.AuthViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule
{
    @Singleton
    @Provides
    fun provideBaseViewModel(repository: BaseRepository): BaseViewModel =
            BaseViewModel(repository)

    @Singleton
    @Provides
    fun provideAuthViewModel(repository: AuthRepository): AuthViewModel = AuthViewModel(repository)
}