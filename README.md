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
