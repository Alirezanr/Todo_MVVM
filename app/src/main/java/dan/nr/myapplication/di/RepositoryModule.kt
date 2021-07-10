package dan.nr.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dan.nr.myapplication.base.BaseRepository
import dan.nr.myapplication.network.AuthApi
import dan.nr.myapplication.network.TodoApi
import dan.nr.myapplication.repository.AuthRepository
import dan.nr.myapplication.repository.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule
{
    @Singleton
    @Provides
    fun provideBaseRepository(): BaseRepository = BaseRepository()

    @Singleton
    @Provides
    fun provideAuthRepository(api: AuthApi): AuthRepository = AuthRepository(api)

    @Singleton
    @Provides
    fun provideTodoRepository(api: TodoApi): HomeRepository = HomeRepository(api)
}