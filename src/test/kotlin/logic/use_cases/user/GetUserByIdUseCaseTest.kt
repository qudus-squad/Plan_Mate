package logic.use_cases.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.exceptions.UserNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.use_cases.user.GetUserByIdUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class GetUserByIdUseCaseTest {

 private lateinit var userRepository: UserRepository
 private lateinit var getUserByIdUseCase: GetUserByIdUseCase

 @BeforeEach
 fun setup() {
  userRepository = mockk(relaxed = true)
  getUserByIdUseCase = GetUserByIdUseCase(userRepository)
 }

 @Test
 fun `should return user by user id when user is found`() {
  runTest {
   // Given
   val user = User(
    username = "Farah",
    passwordHash = "fara-heba-777",
    userId = "731-fafa",
    role = UserRole.ADMIN
   )
   coEvery { userRepository.getUserById("731-fafa") } returns user

   // When
   val result = getUserByIdUseCase.getUserById("731-fafa")

   // Then
   result shouldBe user
  }
 }

 @Test
 fun `should throw UserNotFoundException when there are no users with selected id`() {
  runTest {
   // Given
   coEvery { userRepository.getUserById("999-xyz") } throws UserNotFoundException()

   // When & Then
   shouldThrow<UserNotFoundException> {
    getUserByIdUseCase.getUserById("999-xyz")
   }
  }
 }
}

