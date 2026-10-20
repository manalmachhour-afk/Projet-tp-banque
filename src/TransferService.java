public class TransferService {
//partie 1 du tp
    // EXCEPTION PERSONNALISÉE (classe interne statique)
    public static class InsufficientFundsException extends Exception {

        private final String accountNumber;
        private final double requested;
        private final double available;

        public InsufficientFundsException(String accountNumber,
                                          double requested,
                                          double available) {
            super("Fonds insuffisants sur le compte " + accountNumber
                    + " : demandé=" + requested + ", disponible=" + available);
            this.accountNumber = accountNumber;
            this.requested = requested;
            this.available = available;
        }

        public String getAccountNumber() { return accountNumber; }
        public double getRequested()     { return requested; }
        public double getAvailable()     { return available; }
    }

    // CONSTANTE MÉTIER

    public static final double MAX_TRANSFER_AMOUNT = 10_000.0;


    // MÉTHODE transfer
    public void transfer(BankAccount source,
                         BankAccount destination,
                         double amount)
            throws InsufficientFundsException {

        // ---------- 1. VALIDATION (aucune modification) ----------

        if (source == null || destination == null) {
            throw new IllegalArgumentException(
                    "Les comptes source et destinataire sont obligatoires.");
        }

        if (source.getAccountNumber().equals(destination.getAccountNumber())) {
            throw new IllegalArgumentException(
                    "Virement impossible vers le même compte : "
                            + source.getAccountNumber());
        }

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Le montant doit être strictement positif : " + amount);
        }

        if (amount > MAX_TRANSFER_AMOUNT) {
            throw new IllegalArgumentException(
                    "Le montant " + amount + " dépasse le plafond de "
                            + MAX_TRANSFER_AMOUNT + " €.");
        }

        if (amount > source.getBalance()) {
            throw new InsufficientFundsException(
                    source.getAccountNumber(), amount, source.getBalance());
        }

        // 2. EXÉCUTION (modification des comptes)

        source.debit(amount);
        destination.credit(amount);
    }
}