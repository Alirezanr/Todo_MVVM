package dan.nr.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.base.BaseRepository
import dan.nr.myapplication.base.BaseViewModel
import dan.nr.myapplication.network.RemoteDataSource
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.repository.HomeRepository
import dan.nr.myapplication.util.UserPreferences
import dan.nr.myapplication.viewmodel.AuthViewModel
import dan.nr.myapplication.viewmodel.HomeViewModel
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

    @Singleton
    @Provides
    fun provideHomeViewModel(repository: HomeRepository): HomeViewModel = HomeViewModel(repository)
}