/*
 * Copyright 2010 Jean-Paul Balabanian and Yngve Devik Hammersland
 *
 *     This file is part of glsl4idea.
 *
 *     Glsl4idea is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as
 *     published by the Free Software Foundation, either version 3 of
 *     the License, or (at your option) any later version.
 *
 *     Glsl4idea is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with glsl4idea.  If not, see <http://www.gnu.org/licenses/>.
 */

package glslplugin.lang.elements.expressions;

import com.intellij.lang.ASTNode;
import glslplugin.lang.elements.GLSLElementImpl;
import glslplugin.lang.elements.GLSLTypedElement;
import glslplugin.lang.elements.declarations.GLSLDeclarator;
import glslplugin.lang.elements.declarations.GLSLVariableDeclaration;
import glslplugin.lang.elements.types.GLSLType;
import org.jetbrains.annotations.NotNull;

public class GLSLCondition extends GLSLElementImpl implements GLSLTypedElement {

    public GLSLCondition(@NotNull ASTNode astNode) {
        super(astNode);
    }
    
    private GLSLDeclarator getDeclarator() {
        GLSLVariableDeclaration declaration = getVariableDeclaration();
        if(declaration != null) {
            GLSLDeclarator[] declarators = declaration.getDeclarators();
            if(declarators.length > 0)
                return declarators[0];
        }
        return null;
    }

    public GLSLExpression getConditionExpression()
    {
        GLSLDeclarator declarator = getDeclarator();
        if(declarator != null)
            return declarator.getInitializerExpression();
        return findChildByClass(GLSLExpression.class);
    }
    
    public GLSLVariableDeclaration getVariableDeclaration()
    {
        return findChildByClass(GLSLVariableDeclaration.class);
    }

    @NotNull
    @Override
    public GLSLType getType() {
        GLSLVariableDeclaration declaration = getVariableDeclaration();
        if(declaration != null)
            return declaration.getTypeSpecifierNode().getType();
        
        return getConditionExpression().getType();
    }
}
