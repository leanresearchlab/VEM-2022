package br.udesc.ceavi.pin.infosaude.view.frame.listener;

import br.udesc.ceavi.pin.infosaude.control.excecpton.IdadeMaximaMenorQueIdadeMinimaPublicoAlvoException;
import br.udesc.ceavi.pin.infosaude.view.component.FrameCadastroDeCampanha;
import br.udesc.ceavi.pin.infosaude.view.component.campoDeAcao.InternalFrameCadastrarPessoa;
import br.udesc.ceavi.pin.infosaude.view.component.campoDeAcao.InternalFrameTelaInicial;
import br.udesc.ceavi.pin.infosaude.view.component.campoDeAcao.InternalFrameVacina;
import br.udesc.ceavi.pin.infosaude.view.frame.FramePrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * @author Gustavo C. Santos
 */
public class ListenerMenuUsuarioIntituicao implements Listener {

    private final FramePrincipal tela;
    private JInternalFrame campoAcaoAtual;
    private JInternalFrame campoAcaoObjetivo;
    private final List<JButton> listaDeButao;

    public ListenerMenuUsuarioIntituicao(List<JButton> listaDeButao, FramePrincipal tela) {
        this.tela = tela;
        this.listaDeButao = listaDeButao;
        addActionbtn0();
        addActionbtn1();
        addActionbtn2();
        addActionbtn3();
        addActionbtn4();
        addActionbtn5();
    }
    
    private class btn0AcitionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            addPanel(new InternalFrameTelaInicial());
        }
    }

    private class btn1AcitionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            addPanel(new InternalFrameVacina());
        }
    }

    private class btn2AcitionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            addPanel(new InternalFrameCadastrarPessoa());
        }
    }

    private class btn3AcitionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            FrameCadastroDeCampanha frameCadastroDeCampanha;
			try {
				frameCadastroDeCampanha = new FrameCadastroDeCampanha();
				frameCadastroDeCampanha.setVisible(true);
			} catch (IdadeMaximaMenorQueIdadeMinimaPublicoAlvoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private class btn4AcitionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(tela, "Tela Em Desenvolvimento");
        }
    }

    private class btn5AcitionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(tela, "Telefone : (000) 0800-000 \n"
                    + "Email: infosaude@contato.com");
        }

    }
    
    private void addPanel(JInternalFrame novoFrame) {
    	tela.addPanel(novoFrame);
    }

	@Override
	public void addActionbtn0() {
		listaDeButao.get(0).addActionListener(new btn0AcitionListener());
		
	}

	@Override
	public void addActionbtn1() {
		listaDeButao.get(1).addActionListener(new btn1AcitionListener());
		
	}

	@Override
	public void addActionbtn2() {
		listaDeButao.get(2).addActionListener(new btn2AcitionListener());
		
	}

	@Override
	public void addActionbtn3() {
		listaDeButao.get(3).addActionListener(new btn3AcitionListener());
		
	}

	@Override
	public void addActionbtn4() {
		listaDeButao.get(4).addActionListener(new btn4AcitionListener());
		
	}
	
	public void addActionbtn5() {
		listaDeButao.get(5).addActionListener(new btn5AcitionListener());
	}

}
