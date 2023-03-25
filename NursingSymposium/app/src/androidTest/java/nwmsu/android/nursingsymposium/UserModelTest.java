package nwmsu.android.nursingsymposium;

import nwmsu.android.nursingsymposium.UserModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserModelTest {

    @Mock
    FirebaseAuthRepositoryType firebaseAuthRepositoryType;
    @Mock
    FirebaseUser firebaseUser;

    @InjectMocks
    UserModel userModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUser() {

    }

    @Test
    public void updateUser() {
        //        Mockito.when(firebaseAuthRepositoryType.getCurrentUser)
    }

    @Test
    public void deleteUser() {

    }
}
