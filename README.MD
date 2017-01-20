Locking

	Optimistic - Trust me, i'm russian!

        Parte da premissa de que tudo vai dar certo e que a transação atual será a unica que irá mexer no objeto durante o periodo de execução da transação corrente.
		Antes de persitir a mudança, o provider verifica se alguma mudança foi feita na entidade durante o periodo. 
		Se foi, será lançado OptimisticLockException e a transação marcada para rollback.
        Definido por LockModeType.OPTIMISTIC
        No JPA 1.0 era o LockModeType.READ
        O versionamento da entidade só é incrementado caso haja alguma alteração na entidade.
    
    Optimist Force Increment

        Tem as mesmas caracteristicas do modelo OPTIMISTIC, porem, diferentemente do que acontece no OPTIMISTIC, 
            sempre que a entidade é lida (mesmo que não haja nenhuma alteração nela) seu numero de versionamento será incrementado.
        Definido por LockModeType.OPTIMISTIC_FORCE_INCREMENT
        No JPA 1.0 era o LockModeType.WRITE
        
	Versioning

		O mecanismo usado para verificar se a entidade foi modificada durante seu tempo de vida na memoria é o campo de versionamento.
		Esse é apenas um campo anotado (ou mapeado via XML) com @Versioning
		Esse campo é atualizado automaticamente pelo provider toda vez que uma mudança  a entidade é persistida.
		Funciona assim:

			1 - Entidade é lida do banco (digamos que o campo de versionamento esta com valor 1)
			2 - Entidade tem seus valores alterados em memoria (campo de versionamento continua com valor 1)
			3 - Transação é finalizada e a entidade será atualizada no banco:

				3.1 - Se o valor do campo de versionamento no banco de dados ainda for igual ao da entidade, entidade será persistida
				3.2 - Caso o valor do banco seja maior do que o da entidade (o que indica que ela foi alterada por outra transação), será lançado uma exception.

		Atenção

			O campo de versionamento não é alterado automaticamente caso uma bulk operation seja executada. É de responsabilidade de aplicação incrementar o valor do atributo.


     Pessimistic Locking

        É proativo é trava o objeto de previamente (no momento da leitura), evitando assim que o mesmo possivelmente seja alterado por outra entidade.
        Pro: Nunca será precisa se recuperar de um problema de versionamento ao persistir o objeto porque é garantido, que uma vez ao ler o objeto,
            seu valor será mantido o mesmo até que a transação que tem o lock, o libere.
        Contras: Pode gerar problemas de escalabilidade, pois apenas uma transação terá acesso a determinado objeto/linha


        Pessimistic Write Locking

            Proativo
            Impede que a entidade seja lida por outras transações
        
        Pessimistic Read Locking

            Proativo
            Impede que outras transações alterem o valor da entidade, mas não impede que elas a leiam.
        
        Pessimistic Force Increment

            Igualmente ao seu irmão otimista, esse lock tambem ira alterar o valor da coluna de versionamento, mesmo que nao haja alteração na entidade.

        
        Lock pessimista (qualquer tipo) pode lançar as seguintes exceptions:

            LockTimeoutExceptio:
                Para situações não fatais. 
                Como será lançada antes do commit da transação, a transação corrente não sofrerá rollback.

            PessimisticLockException
                Para situações mais graves.
                Transação marcada para rollback.