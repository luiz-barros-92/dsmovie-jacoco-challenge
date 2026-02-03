# DSMovie
[DevSuperior](https://devsuperior.com.br/)\
<br>
You must implement the following tests to complete the Jacoco coverage.
<br>
## Challenge Status:
### MovieServiceTests
- [ ] `findAllShouldReturnPagedMovieDTO`
- [ ] `findByIdShouldReturnMovieDTOWhenIdExists`
- [ ] `findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- [ ] `insertShouldReturnMovieDTO`
- [ ] `updateShouldReturnMovieDTOWhenIdExists`
- [ ] `updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- [ ] `deleteShouldDoNothingWhenIdExists`
- [ ] `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- [ ] `deleteShouldThrowDatabaseExceptionWhenDependentId`

### ScoreServiceTests
- [ ] `saveScoreShouldReturnMovieDTO`
- [ ] `saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId`

### UserServiceTests
- [ ] `authenticatedShouldReturnUserEntityWhenUserExists`
- [ ] `authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`
- [ ] `loadUserByUsernameShouldReturnUserDetailsWhenUserExists`
- [ ] `loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`
