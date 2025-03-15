package setups;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

public class CleanUpExtension implements BeforeAllCallback {
   @Override
   public void beforeAll(final ExtensionContext context) throws Exception {
      final Collection<CrudRepository> respositories = SpringExtension
              .getApplicationContext(context)
              .getBeansOfType(CrudRepository.class)
              .values();
      this.cleanUp(respositories);
   }

   private void cleanUp(final Collection<CrudRepository> respositories) {
      respositories.forEach(CrudRepository::deleteAll);
   }
}
