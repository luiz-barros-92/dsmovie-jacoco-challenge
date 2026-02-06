# DSMovie
[DevSuperior](https://devsuperior.com.br/)\
<br>
You must implement the following tests to complete the Jacoco coverage.
<br>
## Challenge Status:
### MovieServiceTests
- [x] `findAllShouldReturnPagedMovieDTO`
- [x] `findByIdShouldReturnMovieDTOWhenIdExists`
- [x] `findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- [x] `insertShouldReturnMovieDTO`
- [x] `updateShouldReturnMovieDTOWhenIdExists`
- [x] `updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- [x] `deleteShouldDoNothingWhenIdExists`
- [x] `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`
- [x] `deleteShouldThrowDatabaseExceptionWhenDependentId`

### ScoreServiceTests
- [x] `saveScoreShouldReturnMovieDTO`
- [x] `saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId`

### UserServiceTests
- [ ] `authenticatedShouldReturnUserEntityWhenUserExists`
- [ ] `authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`
- [ ] `loadUserByUsernameShouldReturnUserDetailsWhenUserExists`
- [ ] `loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists`
