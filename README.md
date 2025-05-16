for (Turma turma : turmas) {
            sb.append("Código: ").append(turma.getCodigo()).append("\n");
            sb.append("Período: ").append(turma.getPeriodo()).append("\n");
            sb.append("Capacidade: ").append(turma.getCapacidade()).append("\n");
            sb.append("Data Início: ").append(dateFormat.format(turma.getDataInicio())).append("\n");
            sb.append("Data Término: ").append(dateFormat.format(turma.getDataTermino())).append("\n");

            if (turma.getCurso() != null) {
                sb.append("Curso: ").append(turma.getCurso().getNome()).append("\n");
            }
            List<Aluno> alunos = turma.getAlunos();

            sb.append("Alunos Matriculados: ").append(turma.getAlunos().size()).append("\n");

            for (Aluno aluno : alunos) {
            sb.append(" - ").append(aluno.getNome()).append("\n");
            

        }
    }
        resultadoArea.setText(sb.toString());
    }
