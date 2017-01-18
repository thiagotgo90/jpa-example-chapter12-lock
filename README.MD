Locking

	Optimistic - Trust me, i'm russian!


		Parte da premissa de que tudo vai dar certo e que a transação atual será a unica que irá mexer no objeto durante o periodo de execução da transação corrente.
		Antes de persitir a mudança, o provider verifica se alguma mudança foi feita na entidade durante o periodo. 
		Se foi, será lançado OptimisticLockException e a transação marcada para rollback.

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