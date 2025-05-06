package logic.use_cases.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.qudus.squad.logic.exceptions.UserNotFoundBySelectedUserIdException
import org.qudus.squad.logic.repositories.UserRepository
import org.qudus.squad.logic.use_cases.user.GetUserByIdUseCase
import org.qudus.squad.model.entity.User
import org.qudus.squad.model.entity.UserRole

class GetUserByIdUseCaseTest{
  private lateinit var userRepository: UserRepository
 private lateinit var getUserByIdUseCase:GetUserByIdUseCase

 @BeforeEach
 fun setup(){
  userRepository = mockk(relaxed = true)
  getUserByIdUseCase=GetUserByIdUseCase(userRepository)
 }
 @Test
 fun `should return user by user id when user is found`() {
  // Given
  val user = User(
   username = "Farah",
   passwordHash = "fara-hehe-777",
   userId = "731-fafa",
   role = UserRole.ADMIN
  )
  every { userRepository.getUserById("731-fafa") } returns user

  // When
  val result = getUserByIdUseCase.getUserById("731-fafa")

  // Then
  result shouldBe user
 }

 @Test
 fun `should throw UserNotFoundBySelectedUserIdException when there are no users with selected id`() {
  // Given
  every { userRepository.getUserById(userId = "999-xyz") } throws
          UserNotFoundBySelectedUserIdException(USER_NOT_FOUND_BY_SELECTED_USER_ID)

  // When & Then
  shouldThrow<UserNotFoundBySelectedUserIdException> {
   getUserByIdUseCase.getUserById(userid = "999-xyz")
  }
 }
 companion object {
  const val USER_NOT_FOUND_BY_SELECTED_USER_ID = "There is no user with selected id"
 }
}